package com.example.creatorconnectbackend.services;

import com.example.creatorconnectbackend.interfaces.ConnectionRequestServiceInterface;
import com.example.creatorconnectbackend.models.ConnectionRequest;
import com.example.creatorconnectbackend.models.RequestStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConnectionRequestService implements ConnectionRequestServiceInterface {


    private final JdbcTemplate jdbcTemplate;
    

    private final Logger logger = LoggerFactory.getLogger(ConnectionRequestService.class);

    /**
     * Constructor for ConnectionRequestService.
     *
     * @param jdbcTemplate an instance of JdbcTemplate for database operations.
     */
    public ConnectionRequestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * RowMapper for converting SQL result set rows into ConnectionRequest objects.
     */
    private RowMapper<ConnectionRequest> rowMapper = (rs, rowNum) -> {
        ConnectionRequest connectionRequest = new ConnectionRequest();
        connectionRequest.setRequestID(rs.getLong("RequestID"));
        connectionRequest.setOrgID(rs.getLong("OrgID"));
        connectionRequest.setInfluencerID(rs.getLong("InfluencerID"));
        connectionRequest.setRequestMessage(rs.getString("RequestMessage"));
        connectionRequest.setRequestStatus(RequestStatus.valueOf(rs.getString("RequestStatus")));
        return connectionRequest;
    };

    /**
     * Getter for the rowMapper.
     *
     * @return the rowMapper instance.
     */
    public RowMapper<ConnectionRequest> getRowMapper() {
        return rowMapper;
    }

    /**
     * Creates a new connection request in the database.
     *
     * @param connectionRequest the connection request object to be created.
     * @return the created connection request with a generated ID.
     */
    public ConnectionRequest createRequest(ConnectionRequest connectionRequest) {
        logger.info("Attempting to create connection request.");

        if (connectionRequest != null) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("connection_requests").usingGeneratedKeyColumns("RequestID");

            Map<String, Object> params = new HashMap<>();
            params.put("OrgID", connectionRequest.getOrgID());
            params.put("InfluencerID", connectionRequest.getInfluencerID());
            params.put("RequestMessage", connectionRequest.getRequestMessage());
            params.put("RequestStatus", connectionRequest.getRequestStatus().name());

            Number generatedId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
            connectionRequest.setRequestID(generatedId.longValue());
            logger.info("Connection request created successfully with ID: {}", connectionRequest.getRequestID());

            return connectionRequest;
        } else {
            logger.warn("Connection request creation failed. Provided connection request is null.");
            return null;
        }
    }

    /**
     * Fetches a connection request by its ID.
     *
     * @param requestID the ID of the connection request to fetch.
     * @return the fetched connection request object.
     */
    public ConnectionRequest getConnectionRequestByID(Long requestID) {
        String query = "SELECT * FROM connection_requests WHERE RequestID = ?";
        logger.info("Fetching connection request with ID: {}", requestID);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{requestID}, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Failed to update message for connection request with ID: {}", requestID, e);
            throw new RuntimeException("Connection request not found with ID: " + requestID);
        }
    }

    /**
     * Updates the status of a connection request.
     *
     * @param id the ID of the connection request to update.
     * @param newStatus the new status for the connection request.
     * @return the updated connection request object.
     */
    public ConnectionRequest updateStatus(Long id, RequestStatus newStatus) {
        String query = "UPDATE connection_requests SET RequestStatus = ? WHERE RequestID = ?";
        logger.info("Updating status for connection request with ID: {}", id);
        int updated = jdbcTemplate.update(query, newStatus.name(), id);
        if (updated == 0) {
            logger.error("Failed to update message for connection request with ID: {}", newStatus);
            throw new RuntimeException("Could not update the status of requestID: " + id);
        }
        return getConnectionRequestByID(id);
    }

    /**
     * Fetches all connection requests for a specific influencer.
     *
     * @param id the influencer's ID.
     * @return a list of connection requests associated with the influencer.
     */
    public List<ConnectionRequest> getRequestsByInfluencerID(Long id) {
        String query = "SELECT * FROM connection_requests WHERE InfluencerID = ?";
        try {
            return jdbcTemplate.query(query, new Object[]{id}, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Fetches all connection requests for a specific organization.
     *
     * @param orgID the organization's ID.
     * @return a list of connection requests associated with the organization.
     */
    public List<ConnectionRequest> getRequestsByOrgID(Long orgID) {
        String query = "SELECT * FROM connection_requests WHERE OrgID = ?";
        try {
            return jdbcTemplate.query(query, new Object[]{orgID}, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Fetches all connection requests for a specific organization and status.
     *
     * @param orgID the organization's ID.
     * @param status the status of the connection requests.
     * @return a list of connection requests associated with the organization and status.
     */
    public List<ConnectionRequest> getRequestsByStatus(Long orgID, String status) {
        String query = "SELECT * FROM connection_requests WHERE OrgID = ? AND RequestStatus = ?";
        try {
            return jdbcTemplate.query(query, new Object[]{orgID, status}, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Fetches all connection requests.
     *
     * @return a list of all connection requests.
     */
    public List<ConnectionRequest> getAllRequests() {
        String query = "SELECT * FROM connection_requests";
        try {
            return jdbcTemplate.query(query, new Object[]{}, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Deletes a connection request by its ID.
     *
     * @param id the ID of the connection request to delete.
     */
    public void deleteByID(Long id) {
        String query = "DELETE FROM connection_requests WHERE RequestID = ?";
        logger.info("Deleting connection request with ID: {}", id);
        int deletedRows = jdbcTemplate.update(query, id);
        if (deletedRows == 0) {
            logger.error("Failed to update message for connection request with ID: {}", id);
            throw new RuntimeException("Failed to delete connection request with ID: " + id);
        }
    }

    /**
     * Updates the message of a connection request.
     *
     * @param id the ID of the connection request to update.
     * @param map a map containing the new message.
     * @return the updated connection request object.
     */
    public ConnectionRequest updateMessage(Long id, Map<String, String> map) {
        String query = "UPDATE connection_requests SET RequestMessage = ? WHERE RequestID = ?";
        logger.info("Updating message for connection request with ID: {}", id);
        String message = map.get("Message");
        int updatedRows = jdbcTemplate.update(query, message, id);
        if (updatedRows == 0) {
            logger.error("Failed to update message for connection request with ID: {}", id);
            throw new RuntimeException("Failed to update message for connection request with ID: " + id);
        }
        return getConnectionRequestByID(id);
    }
}
