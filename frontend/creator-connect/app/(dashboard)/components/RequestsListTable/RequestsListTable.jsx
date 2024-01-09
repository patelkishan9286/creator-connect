import * as React from "react";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";
import data from "./data.js";
import { Button } from "@mui/material";
import Link from "next/link";

const columns = [
  { id: "requestID", label: "Request Id", minWidth: 100 },
  { id: "requestMessage", label: "Request Message", minWidth: 300 },
  {
    id: "requestStatus",
    label: "Request Status",
    minWidth: 100,
    align: "center",
    format: (value) => value.toLocaleString("en-US"),
  },
  {
    id: "organization",
    label: "",
    minWidth: 170,
    align: "center",
    format: (value) => <Button variant="contained">{v}</Button>,
  },
];

export default function RequestsListTable({ requests }) {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  return (
    <Paper sx={{ width: "100%", overflow: "hidden" }}>
      <TableContainer sx={{ maxHeight: 440 }}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              {columns.map((column) => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth }}
                >
                  {column.label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {requests ? (
              requests
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((requests) => {
                  return (
                    <TableRow
                      hover
                      role="checkbox"
                      tabIndex={-1}
                      key={requests.id}
                    >
                      {columns.map((column) => {
                        const value = requests[column.id];

                        if (column.id === "organization") {
                          return (
                            <TableCell key={column.id} align={column.align}>
                              <Link
                                href={`/organization-profile/${requests.orgID}/${requests.requestID}`}
                              >
                                <Button
                                  variant="contained"
                                  sx={{ backgroundColor: "#222AEF" }}
                                >
                                  View Company
                                </Button>
                              </Link>
                            </TableCell>
                          );
                        } else {
                          return (
                            <TableCell key={column.id} align={column.align}>
                              {column.format && typeof value === "number"
                                ? column.format(value)
                                : value}
                            </TableCell>
                          );
                        }
                      })}
                    </TableRow>
                  );
                })
            ) : (
              <TableRow>
                <TableCell>Loading...</TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[10, 25, 100]}
        component="div"
        count={requests ? requests.length : 0}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Paper>
  );
}
