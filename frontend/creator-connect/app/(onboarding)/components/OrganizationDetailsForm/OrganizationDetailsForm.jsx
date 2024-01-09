"use client";

import FacebookIcon from "@mui/icons-material/Facebook";
import InstagramIcon from "@mui/icons-material/Instagram";
import TwitterIcon from "@mui/icons-material/Twitter";
import YouTubeIcon from "@mui/icons-material/YouTube";
import { Grid } from "@mui/material";
import React from "react";
import styles from "./OrganizationDetailsForm.module.css";
import { useOrganizationDetailsForm } from "./useOrganizationDetailsForm";

export const OrganizationDetailsForm = () => {
  const {
    organizationNicheList,
    handleSelect,
    filterSelected,
    selectedNiches,
    setSelectedNiches,
    instagramUrl,
    setInstagramUrl,
    twitterUrl,
    setTwitterUrl,
    youtubeUrl,
    setYoutubeUrl,
    facebookUrl,
    setFacebookUrl,
    industry,
    setIndustry,
    handleFinish,
  } = useOrganizationDetailsForm();

  return (
    <Grid
      rowGap={2}
      style={{
        display: "flex",
        flexDirection: "column",
        width: "60%",
      }}
    >
      <div style={{ display: "flex", flexDirection: "column" }}>
        <div style={{ alignSelf: "flex-start" }}>
          <p
            style={{
              fontSize: "20px",
              color: "#222aef",
              fontWeight: "bold",
              textAlign: "left",
            }}
          >
            Select the type of creators you are interested in
          </p>
          <p style={{ textAlign: "left", marginTop: "5px" }}>
            Select all that apply
          </p>
        </div>
        <div
          style={{ display: "flex", flexDirection: "row", flexWrap: "wrap" }}
        >
          {selectedNiches.map((niche, index) => {
            return (
              <div
                style={
                  niche.selected
                    ? {
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        padding: "10px",
                        height: "40px",
                        marginTop: "20px",
                        marginRight: "20px",
                        borderRadius: "15px",
                        color: "white",
                        backgroundColor: "#222aef",
                      }
                    : {
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        padding: "10px",
                        height: "40px",
                        marginTop: "20px",
                        marginRight: "20px",
                        borderRadius: "15px",
                        backgroundColor: "#d2d3d1",
                      }
                }
                onClick={() =>
                  handleSelect(selectedNiches, setSelectedNiches, index)
                }
              >
                <span>{niche.name}</span>
              </div>
            );
          })}
        </div>
      </div>

      <div
        style={{ display: "flex", flexDirection: "column", overflow: "scroll" }}
      >
        <div style={{ alignSelf: "flex-start" }}>
          <p
            style={{
              fontSize: "20px",
              color: "#222aef",
              fontWeight: "bold",
              textAlign: "left",
            }}
          >
            Add your socials
          </p>
          <p style={{ textAlign: "left", marginTop: "5px" }}>
            Add all that apply
          </p>
        </div>
        <Grid container rowGap={2}>
          <div
            style={{
              display: "flex",
              width: "100%",
              alignItems: "center",
              justifyContent: "space-between",
              marginTop: "20px",
            }}
          >
            <InstagramIcon />
            <input
              className={styles.input}
              placeholder="URL"
              style={{ width: "90%" }}
              value={instagramUrl}
              onChange={(e) => setInstagramUrl(e.target.value)}
            />
          </div>
          <div
            style={{
              display: "flex",
              width: "100%",
              alignItems: "center",
              justifyContent: "space-between",
            }}
          >
            <TwitterIcon />
            <input
              className={styles.input}
              placeholder="URL"
              style={{ width: "90%" }}
              value={twitterUrl}
              onChange={(e) => setTwitterUrl(e.target.value)}
            />
          </div>
          <div
            style={{
              display: "flex",
              width: "100%",
              alignItems: "center",
              justifyContent: "space-between",
            }}
          >
            <YouTubeIcon />
            <input
              className={styles.input}
              placeholder="URL"
              style={{ width: "90%" }}
              value={youtubeUrl}
              onChange={(e) => setYoutubeUrl(e.target.value)}
            />
          </div>
          <div
            style={{
              display: "flex",
              width: "100%",
              alignItems: "center",
              justifyContent: "space-between",
            }}
          >
            <FacebookIcon />
            <input
              className={styles.input}
              placeholder="URL"
              style={{ width: "90%" }}
              value={facebookUrl}
              onChange={(e) => setFacebookUrl(e.target.value)}
            />
          </div>
        </Grid>

        <select
          className={styles.input}
          style={{ marginTop: "2rem" }}
          value={industry}
          onChange={(e) => setIndustry(e.target.value)}
        >
          <option value="" selected disabled hidden>
            Choose Organization Industry
          </option>
          <option value="FoodManufacturing">Food Manufacturing</option>
          <option value="Construction">Construction</option>
          <option value="InformationTechnology">Information Technology</option>
          <option value="Healthcare">Healthcare</option>
          <option value="Automotive">Automotive</option>
          <option value="Retail">Retail</option>
          <option value="Finance">Finance</option>
          <option value="Education">Education</option>
          <option value="Energy">Energy</option>
          <option value="Transportation">Transportation</option>
          <option value="Food">Food</option>
        </select>
      </div>
      <button className={styles.button} onClick={handleFinish}>
        Finish
      </button>
    </Grid>
  );
};
