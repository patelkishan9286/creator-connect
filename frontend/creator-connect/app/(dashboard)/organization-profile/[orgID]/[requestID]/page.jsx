"use client";
import {
  Container,
  Grid,
  Paper,
  Avatar,
  Button,
  Typography,
  Chip,
  Box,
} from "@mui/material";
import { useEffect, useState } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";

import { makeStyles } from "@mui/styles";
import SocialMediaIcons from "../../../components/SocialMediaIcons/SocialMediaIcons";
const useStyles = makeStyles({
  large: {
    width: "100%",
    height: "400px",
    borderRadius: 0,
    objectFit: "cover",
  },
  buttonContainer: {
    position: "absolute",
    bottom: "20px",
    right: "25px",
    width: "500px",
    display: "flex",
    // flexDirection: "row",
    // justifyContent: "space-evenly",
    justifyContent: "flex-end",
  },
  button: {
    marginLeft: "20px",
  },
  name: {
    position: "absolute",
    bottom: "20px",
    left: "25px",
    fontWeight: "bold",
    color: "white",
    backgroundColor: "rgba(0, 0, 0, 0.3)",
    padding: "5px",
  },
  coverContainer: {
    position: "relative",
    margin: "0 0",
  },
});
export default function OrganizationProfile({ params }) {
  const classes = useStyles();
  const organizationID = params.orgID;
  const requestID = params.requestID;
  const [organizationData, setOrganizationData] = useState(null);
  const router = useRouter();

  useEffect(() => {
    const fetchOrganizationData = async () => {
      try {
        const res = await axios.get(
          "http://localhost:8080/api/organizations/" + organizationID
        );
        setOrganizationData(res.data);
        console.log(res.data);
      } catch (error) {
        console.log("Error:");
        console.error(error);
      }
    };

    fetchOrganizationData();
  }, []);

  const updateRequestStatus = async (status) => {
    try {
      const newStatus = {
        requestStatus: status,
      };

      const res = await axios.put(
        "http://localhost:8080/api/connectionReq/update/" + requestID,
        newStatus
      );

      router.back();

      console.log(res.data);
      toast.success(res.data);
    } catch (error) {
      console.log("Error:");
      console.error(error);
    }
  };

  return (
    <Container maxWidth="lg">
      <Paper
        sx={{
          margin: "auto",
          flexGrow: 1,
          backgroundColor: "#e8eae0",
          textTransform: "none",
        }}
      >
        <Grid container direction="column">
          <Grid
            item
            xs={12}
            className={classes.coverContainer}
            display={"flex"}
            justifyContent={"row"}
          >
            <Avatar
              alt="Cover Image"
              src="https://www.resolutionlawng.com/wp-content/uploads/2020/09/group-of-company-img.jpeg"
              className={classes.large}
              variant="square"
            />
            <Box className={classes.buttonContainer}>
              <Button
                variant="contained"
                color="primary"
                className={classes.button}
                onClick={() => updateRequestStatus("Accepted")}
              >
                Accept
              </Button>
              <Button
                variant="contained"
                color="secondary"
                className={classes.button}
                onClick={() => updateRequestStatus("Denied")}
              >
                Reject
              </Button>
            </Box>
            <Typography variant="h6" className={classes.name}>
              {organizationData?.orgName}
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <Grid container direction="row">
              <Grid item xs={12} md={4}>
                <Grid container direction="column" spacing={1}>
                  <Grid item xs={12} mt={2}>
                    <SocialMediaIcons
                      links={{
                        instagram: organizationData?.instagram,
                        youtube: organizationData?.youtube,
                        twitter: organizationData?.tweeter,
                        facebook: organizationData?.facebook,
                        tiktok: organizationData?.tikTok,
                        twitch: organizationData?.twitch,
                      }}
                    />
                  </Grid>

                  <Grid item xs={12} ml={1}>
                    <Typography variant="body1">
                      {organizationData?.location}
                    </Typography>
                  </Grid>
                  <Grid item xs={12} ml={1}>
                    <Typography variant="body2">
                      Industry: {organizationData?.companyType}
                    </Typography>
                  </Grid>

                  <Grid item xs={12} ml={1}>
                    <Typography variant="body2">
                      Types of influencers we are looking for:
                    </Typography>
                  </Grid>
                  <Grid
                    item
                    xs={12}
                    display={"flex"}
                    justifyContent={"flex-start"}
                  >
                    {organizationData?.targetInfluencerNiche.map((niche) => (
                      <Chip
                        key={niche}
                        label={niche}
                        sx={{
                          ml: 1,
                          mb: 1,
                          backgroundColor: "#rgba(232, 234, 224, 0.4)",
                        }}
                      />
                    ))}
                  </Grid>
                  <Grid item xs={12}>
                    <Paper
                      variant="outlined"
                      borderRadius="20px"
                      sx={{
                        backgroundColor: "#F1F3E6",
                        p: 1,
                        border: "1px solid #222AEF",
                      }}
                    >
                      <Box p={2}>
                        <Grid item xs={12}>
                          <Typography variant="body2">About us:</Typography>
                        </Grid>
                        <Typography variant="body1">
                          {organizationData?.bio}
                        </Typography>
                      </Box>
                    </Paper>
                  </Grid>
                </Grid>
              </Grid>
              <Grid item xs={12} md={8}>
                <Grid container direction="column">
                  <Grid item xs={12} mt={2} textAlign={"center"}>
                    <Typography variant="h4">Featured Posts</Typography>
                  </Grid>
                  <Grid item container xs={12} rowGap={2}>
                    <Paper
                      variant="outlined"
                      borderRadius="20px"
                      sx={{
                        backgroundColor: "#F1F3E6",
                        p: 1,
                        border: "1px solid #222AEF",
                        height: "200px",
                        width: "300px",
                        margin: "auto",
                      }}
                    />
                    <Paper
                      variant="outlined"
                      borderRadius="20px"
                      sx={{
                        backgroundColor: "#F1F3E6",
                        p: 1,
                        border: "1px solid #222AEF",
                        height: "200px",
                        width: "300px",
                        margin: "auto",
                      }}
                    />
                    <Paper
                      variant="outlined"
                      borderRadius="20px"
                      sx={{
                        backgroundColor: "#F1F3E6",
                        p: 1,
                        border: "1px solid #222AEF",
                        height: "200px",
                        width: "300px",
                        margin: "auto",
                      }}
                    />
                    <Paper
                      variant="outlined"
                      borderRadius="20px"
                      sx={{
                        backgroundColor: "#F1F3E6",
                        p: 1,
                        border: "1px solid #222AEF",
                        height: "200px",
                        width: "300px",
                        margin: "auto",
                      }}
                    />
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Paper>
    </Container>
  );
}
