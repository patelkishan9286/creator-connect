import * as React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { Grid, Button } from "@mui/material";
import { useEffect, useState } from "react";
import axios from "axios";

import PropTypes from "prop-types";
import { styled } from "@mui/material/styles";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import { Link } from "next/link";
import { useRouter } from "next/navigation";

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
  "& .MuiDialogContent-root": {
    padding: theme.spacing(2),
  },
  "& .MuiDialogActions-root": {
    padding: theme.spacing(1),
  },
}));

function BootstrapDialogTitle(props) {
  const { children, onClose, ...other } = props;

  return (
    <DialogTitle sx={{ m: 0, p: 2 }} {...other}>
      {children}
      {onClose ? (
        <IconButton
          aria-label="close"
          onClick={onClose}
          sx={{
            position: "absolute",
            right: 8,
            top: 8,
            color: (theme) => theme.palette.grey[500],
          }}
        >
          <CloseIcon />
        </IconButton>
      ) : null}
    </DialogTitle>
  );
}

BootstrapDialogTitle.propTypes = {
  children: PropTypes.node,
  onClose: PropTypes.func.isRequired,
};

export default function RequestCard({
  requestID,
  orgID,
  influencerID,
  requestMessage,
  requestStatus,
}) {
  const [influencerData, setInfluencerData] = useState(null);
  const [open, setOpen] = React.useState(false);
  const router = useRouter();

  const navigateToInfluencerProfile = () => {
    router.push(`/influencer-profile/${influencerID}`);
  };
  const handleClickOpen = () => {
    console.log("clicked");
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  useEffect(() => {
    const fetchInfluencerData = async () => {
      try {
        const res = await axios.get(
          "http://localhost:8080/api/influencers/" + influencerID
        );
        setInfluencerData(res.data);
        console.log(res.data);
      } catch (error) {
        console.log("Error:");
        console.error(error);
      }
    };

    fetchInfluencerData();
  }, []);

  return (
    <Card
      onClick={navigateToInfluencerProfile}
      sx={{ maxHeight: 150, backgroundColor: "#D9D9D9", cursor: "pointer" }}
    >
      <Grid container>
        <Grid item xs={4}>
          <CardMedia
            component="img"
            image="https://img.freepik.com/free-vector/influencer-concept-illustration_114360-681.jpg"
            alt="influencerImage"
            sx={{
              objectFit: "cover",
              height: "100%",
              width: "100%",
            }}
          />
        </Grid>
        <Grid item xs={8}>
          <CardContent>
            <Grid container textAlign="left">
              <Grid item xs={12}>
                <Typography
                  gutterBottom
                  variant="h6"
                  component="div"
                  sx={{ textTransform: "none" }}
                >
                  {influencerData?.name}
                </Typography>
              </Grid>
              <Grid item xs={12}>
                <Button
                  variant="contained"
                  color={
                    requestStatus === "Accepted"
                      ? "success"
                      : requestStatus === "Pending"
                      ? "warning"
                      : "error"
                  }
                  size="small"
                  sx={{ borderRadius: "50px", textTransform: "none" }}
                >
                  {requestStatus}
                </Button>
              </Grid>
              <Grid item xs={12} mt={1}>
                <Button
                  variant="contained"
                  onClick={(e) => {
                    e.stopPropagation();
                    handleClickOpen();
                  }}
                  sx={{
                    borderRadius: "50px",
                    textTransform: "none",
                    backgroundColor: "#e8eae0",
                    color: "#000000",
                    "&:hover": {
                      backgroundColor: "rgba(232, 234, 224, 0.4)",
                      color: "#000000",
                    },
                  }}
                  size="small"
                >
                  View Request
                </Button>
                <BootstrapDialog onClose={handleClose} open={open}>
                  <BootstrapDialogTitle
                    id="customized-dialog-title"
                    onClose={handleClose}
                    minWidth="250px"
                  >
                    Request Message
                  </BootstrapDialogTitle>
                  <DialogContent dividers>
                    <Typography gutterBottom>{requestMessage}</Typography>
                  </DialogContent>
                </BootstrapDialog>
              </Grid>
            </Grid>
          </CardContent>
        </Grid>
      </Grid>
    </Card>
  );
}
