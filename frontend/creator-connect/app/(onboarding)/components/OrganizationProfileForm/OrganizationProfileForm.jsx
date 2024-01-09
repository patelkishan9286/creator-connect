"use client";

import React from "react";
import styles from "./OrganizationProfileForm.module.css";
import { Avatar, Grid } from "@mui/material";
import { useOrganizationProfileForm } from "./useOrganizationProfileForm";

export const OrganizationProfileForm = () => {
  const {
    organizationName,
    setOrganizationName,
    description,
    setDescription,
    websiteLink,
    setWebsiteLink,
    region,
    setRegion,
    organizationSize,
    setOrganizationSize,
    handleNext,
  } = useOrganizationProfileForm();

  return (
    <Grid
      rowGap={2}
      style={{
        display: "flex",
        flexDirection: "column",
        width: "60%",
      }}
    >
      <div
        style={{
          alignSelf: "center",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        {organizationName ? (
          <Avatar
            alt="CreatorConnect"
            sx={{
              bgcolor: "#222AEF",
              color: "#D9D9D9",
              height: 100,
              width: 100,
              mb: 2,
              fontSize: "6rem",
            }}
          >
            {`${organizationName?.[0].toUpperCase()}`}
          </Avatar>
        ) : (
          <Avatar
            alt="CreatorConnect"
            sx={{
              bgcolor: "#222AEF",
              color: "#D9D9D9",
              height: 100,
              width: 100,
              mb: 2,
              fontSize: "6rem",
            }}
          >
            C
          </Avatar>
        )}
      </div>
      <input
        className={styles.input}
        placeholder="Organization name"
        value={organizationName}
        onChange={(e) => setOrganizationName(e.target.value)}
      />
      <input
        className={styles.input}
        placeholder="Organization description"
        style={{ height: "9rem", textAlign: "top" }}
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />
      <input
        className={styles.input}
        placeholder="Website link"
        value={websiteLink}
        onChange={(e) => setWebsiteLink(e.target.value)}
      />

      <select
        className={styles.input}
        value={region}
        onChange={(e) => setRegion(e.target.value)}
      >
        <option value="" selected disabled hidden>
          Choose Region
        </option>
        <option value="North America">North America</option>
        <option value="volvo">South America</option>
        <option value="Caribbean">Caribbean</option>
        <option value="Asia">Asia</option>
        <option value="Europe">Europe</option>
        <option value="Australasia">Australasia</option>
        <option value="Africa">Africa</option>
      </select>
      <select
        className={styles.input}
        value={organizationSize}
        onChange={(e) => setOrganizationSize(e.target.value)}
      >
        <option value="" selected disabled hidden>
          Choose Organization Size
        </option>
        <option value="49">10-49 </option>
        <option value="150">Medium: 50-249 employees</option>
        <option value="249">Large: 249+ employees</option>
      </select>
      <button className={styles.button} onClick={handleNext}>
        Next
      </button>
    </Grid>
  );
};
