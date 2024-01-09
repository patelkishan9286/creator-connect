"use client";
import { Box, Container, Grid, Typography, Tab, Tabs } from "@mui/material";
import { useState, useEffect } from "react";
import dynamic from "next/dynamic";
import axios from "axios";
import RequestsListTable from "../components/RequestsListTable/RequestsListTable";

export default function InfluencerDashboard() {
  const [requests, setRequests] = useState(null);
  const [profileViews, setProfileViews] = useState();
  let userData;
  let userID;
  if (typeof window !== "undefined") {
    userData = localStorage.getItem("userData");
    userData = JSON.parse(userData);
    userID = userData.userID;
  }

  useEffect(() => {
    const fetchRequests = async () => {
      try {
        const token = localStorage.getItem("token");
        const res = await fetch(
          `/api/proxy?url=connectionReq/influencer/getByID/${userID}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (res.status < 400) {
          const result = await res.json();
          let pendingRequests = result.filter(
            (request) => request.requestStatus === "Pending"
          );
          setRequests(pendingRequests);
        } else {
          console.error("An error occurred.");
        }
      } catch (error) {
        console.error("Error: " + error);
      }
    };

    const fetchCount = async () => {
      try {
        const token = localStorage.getItem("token");
        const res = await fetch(
          `/api/proxy?url=viewCounters/getByID/${userID}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (res.status < 400) {
          const result = await res.json();
          if (result.data) {
            setProfileViews(result.data[userID]);
          }
        } else {
          console.error("An error occurred.");
        }
      } catch (error) {
        console.error("Error: " + error);
      }
    };

    fetchCount();

    fetchRequests();
  }, []);

  const Placeholder = () => <div>Loading...</div>;

  const RequestCard = dynamic(
    () => import("../components/RequestCard/RequestCard"),
    {
      ssr: false,
      loading: () => <Placeholder />,
    }
  );

  const RecommendedInfluencerCard = dynamic(
    () =>
      import(
        "../components/RecommendedInfluencerCard/RecommendedInfluencerCard"
      ),
    {
      ssr: false,
      loading: () => <Placeholder />,
    }
  );

  return (
    <Box height="100vh" overflow="auto">
      <Grid container spacing={2} direction="column" mt={7}>
        <Container maxWidth="lg">
          <Grid container spacing={2} direction="column">
            <Grid item xs={12}>
              <Typography variant="h5" color="#222AEF" fontWeight="600" mt={3}>
                Requests List
              </Typography>
            </Grid>
            <Grid item xs={12}>
              {requests?.length ? (
                <RequestsListTable requests={requests} />
              ) : (
                <Box
                  sx={{
                    backgroundColor: "#b7fafc",
                    border: "1px solid blue",
                    borderRadius: "10px",
                  }}
                  p={5}
                >
                  <Typography
                    variant="h5"
                    color="#222AEF"
                    fontWeight="600"
                    mt={3}
                    textAlign={"center"}
                  >
                    You have no requests a this time. Try improving your
                    profile!
                  </Typography>
                </Box>
              )}
            </Grid>
          </Grid>
          {profileViews ? (
            <Grid container spacing={2} direction="column">
              <Grid item xs={12}>
                <Typography
                  variant="h5"
                  color="#222AEF"
                  fontWeight="600"
                  mt={3}
                >
                  Profile Views
                </Typography>
              </Grid>
              <Grid item xs={12}>
                <Box
                  sx={{
                    backgroundColor: "#b7fafc",
                    border: "1px solid blue",
                    borderRadius: "10px",
                  }}
                  p={5}
                >
                  <Typography
                    variant="h5"
                    color="#222AEF"
                    fontWeight="600"
                    mt={3}
                    textAlign={"center"}
                  >
                    {profileViews}
                  </Typography>
                </Box>
              </Grid>
            </Grid>
          ) : null}
        </Container>
      </Grid>
    </Box>
  );
}
