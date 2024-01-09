"use client";

import React, { useEffect } from "react";
import styles from "./InfluencerProfileForm.module.css";
import { Avatar, Grid } from "@mui/material";
import { useInfluencerProfileForm } from "./useInfluencerProfileForm";
import { useRouter } from "next/navigation";

export const InfluencerProfileForm = () => {
  const router = useRouter();
  useEffect(() => {
    // Retrieve JWT token from local storage
    let tempToken;
    if (window !== undefined) {
      tempToken = localStorage.getItem("token");
    }

    // If token does not exist, redirect to login page
    if (!localStorage.getItem("token")) {
      alert("Please login to continue");
      // router.push("/login");
      return;
    }
  }, []);

  const {
    firstName,
    setFirstName,
    lastName,
    setLastName,
    bio,
    setBio,
    gender,
    setGender,
    region,
    setRegion,
    birthdate,
    setBirthdate,
    handleNext,
    token,
    setToken,
  } = useInfluencerProfileForm();

  console.log("Outside useEffect");
  console.log(token?.length);
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
        {firstName && lastName ? (
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
            {`${firstName?.[0].toUpperCase() + lastName?.[0].toUpperCase()}`}
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

      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <input
          className={styles.input}
          style={{ width: "49%" }}
          placeholder="First name"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
        <input
          className={styles.input}
          style={{ width: "49%" }}
          placeholder="Last name"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
      </div>
      <input
        className={styles.input}
        placeholder="Bio"
        value={bio}
        onChange={(e) => setBio(e.target.value)}
        style={{ height: "9rem" }}
      />
      <select
        className={styles.input}
        value={gender}
        onChange={(e) => setGender(e.target.value)}
      >
        <option value="" selected disabled hidden>
          Choose Gender
        </option>
        <option value="MALE">Male</option>
        <option value="FEMALE">Female</option>
      </select>
      <select
        className={styles.input}
        value={region}
        onChange={(e) => setRegion(e.target.value)}
      >
        <option value="" selected disabled hidden>
          Choose Region
        </option>
        <option value="North America">North America</option>
        <option value="South America">South America</option>
        <option value="Caribbean">Caribbean</option>
        <option value="Asia">Asia</option>
        <option value="Europe">Europe</option>
        <option value="Australasia">Australasia</option>
        <option value="Africa">Africa</option>
      </select>
      <input
        className={styles.input}
        type="date"
        min="1920-01-01"
        max="2018-12-31"
        value={birthdate}
        onChange={(e) => setBirthdate(e.target.value)}
      />
      <button className={styles.button} onClick={handleNext}>
        Next
      </button>
    </Grid>
  );
};
