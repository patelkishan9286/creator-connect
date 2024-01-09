package com.example.creatorconnectbackend.services;

import com.example.creatorconnectbackend.interfaces.ViewCounterServiceInterface;
import com.example.creatorconnectbackend.models.ViewCounter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ViewCounterService implements ViewCounterServiceInterface {
    private final JdbcTemplate jdbcTemplate;

    public ViewCounterService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * RowMapper for ViewCounter objects, used to map a row in a ResultSet to a ViewCounter object.
     *
     * @return RowMapper for ViewCounter objects.
     */
    private RowMapper<ViewCounter> rowMapper = (rs, rowNum) -> {
        ViewCounter viewCounter = new ViewCounter();
        viewCounter.setOrgId(rs.getLong("OrgID"));
        viewCounter.setInfluencerId(rs.getLong("InfluencerID"));
        viewCounter.setDate(rs.getDate("Date"));
        return viewCounter;
    };

    public RowMapper<ViewCounter> getRowMapper() {
        return rowMapper;
    }

    /**
     * Adds a new ViewCounter.
     *
     * @param viewCounter The ViewCounter to be added.
     * @return The added ViewCounter. Returns null if there was an exception.
     */
    public ViewCounter addView(ViewCounter viewCounter) {
        try {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("View_counter");

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("InfluencerID", viewCounter.getInfluencerId());
            parameters.put("OrgID", viewCounter.getOrgId());
            parameters.put("Date", viewCounter.getDate());

            jdbcInsert.execute(parameters);
            return viewCounter;
        } catch (Exception e) {
            // Return null if an exception occurs during the view addition
            return null;
        }
    }

    /**
     * Retrieves the number of views for a particular influencer.
     *
     * @param id The ID of the influencer.
     * @return A map where the key is the influencer's ID and the value is the number of views.
     */
    public Map<Long, Integer> getViewsByInfluencerID(Long id) {
        String query = "SELECT COUNT(*) AS profile_views FROM View_counter WHERE InfluencerID = ?";
        try {
            return jdbcTemplate.query(query, new Object[]{id}, (rs, rowNum) -> { int viewCount = rs.getInt("profile_views");
                return new AbstractMap.SimpleEntry<>(id, viewCount);
            }).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (EmptyResultDataAccessException e) {
            Map<Long, Integer> map = new HashMap<>();
            map.put(id, 0);
            return map;
        }
    }

    /**
     * Retrieves the number of views for an influencer's profile based on the company type.
     *
     * @param id The ID of the influencer.
     * @return A map where the key is the company type and the value is the number of views.
     */
    public Map<String, Integer> getProfileViewsByCompanyType(Long id) {
        String sql = "SELECT o.CompanyType, COUNT(*) AS view_count " +
                "FROM View_counter v " +
                "JOIN organizations o ON v.OrgID = o.OrgID " +
                "WHERE v.InfluencerID = ? " +
                "GROUP BY o.CompanyType";

        try {
            return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {  String companyType = rs.getString("companyType");int viewCount = rs.getInt("view_count");
                return new AbstractMap.SimpleEntry<>(companyType, viewCount);
            }).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (Exception e) {
            // If an exception occurs during the query, return an empty map
            return new HashMap<>();
        }
    }
}
