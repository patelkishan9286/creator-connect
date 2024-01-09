import React from "react";
import InstagramIcon from "@mui/icons-material/Instagram";
import TwitterIcon from "@mui/icons-material/Twitter";
import YouTubeIcon from "@mui/icons-material/YouTube";
import FacebookIcon from "@mui/icons-material/Facebook";
import { IconButton, Grid } from "@mui/material";
import Image from "next/image";
import TwitchLogo from "../../icons/twitch.svg";
import TikTokLogo from "../../icons/tiktok.svg";

export default function SocialMediaIcons({ links }) {
  return (
    <Grid container justifyContent="flex-start">
      {links?.instagram ? (
        <Grid item xs={2}>
          <IconButton
            color="primary"
            component="a"
            href={links.instagram || ""}
            target="_blank"
            rel="noopener noreferrer"
            disabled={!links.instagram}
          >
            <InstagramIcon style={{ color: "#E1306C" }} />
          </IconButton>
        </Grid>
      ) : null}
      {links?.twitter ? (
        <Grid item xs={2}>
          <IconButton
            color="primary"
            component="a"
            href={links.twitter || ""}
            target="_blank"
            rel="noopener noreferrer"
            disabled={!links.twitter}
          >
            <TwitterIcon style={{ color: "#1DA1F2" }} />
          </IconButton>
        </Grid>
      ) : null}
      {links?.tiktok ? (
        <Grid item xs={2}>
          <IconButton
            color="primary"
            component="a"
            href={links.tiktok || ""}
            target="_blank"
            rel="noopener noreferrer"
            disabled={!links.tiktok}
          >
            <Image src={TikTokLogo} width={24} height={24} alt="TikTok Logo" />
          </IconButton>
        </Grid>
      ) : null}
      {links?.youtube ? (
        <Grid item xs={2}>
          <IconButton
            color="primary"
            component="a"
            href={links.youtube || ""}
            target="_blank"
            rel="noopener noreferrer"
            disabled={!links.youtube}
          >
            <YouTubeIcon style={{ color: "#FF0000" }} />
          </IconButton>
        </Grid>
      ) : null}
      {links?.facebook ? (
        <Grid item xs={2}>
          <IconButton
            color="primary"
            component="a"
            href={links.facebook || ""}
            target="_blank"
            rel="noopener noreferrer"
            disabled={!links.facebook}
          >
            <FacebookIcon style={{ color: "#4267B2" }} />
          </IconButton>
        </Grid>
      ) : null}
      {links?.twitch ? (
        <Grid item xs={2}>
          <IconButton
            color="primary"
            component="a"
            href={links.twitch || ""}
            target="_blank"
            rel="noopener noreferrer"
            disabled={!links.twitch}
          >
            <Image src={TwitchLogo} width={24} height={24} alt="Twitch Logo" />
          </IconButton>
        </Grid>
      ) : null}
    </Grid>
  );
}
