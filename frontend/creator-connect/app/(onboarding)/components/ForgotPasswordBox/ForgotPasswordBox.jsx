"use client";
import { Grid } from "@mui/material";
import Link from "next/link";
import React, { useState } from "react";
// import { useRouter } from "next/router";
import styles from "./ForgotPasswordBox.module.css";

export const ForgotPasswordBox = () => {
  const [email, setEmail] = useState("");
  // const router = useRouter();

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };

  const handleClick = async () => {
    const response = await fetch(
      "http://localhost:8080/api/users/forgot-password",
      {
        method: "POST",

        body: JSON.stringify({ email }),
      }
    );

    if (response.ok) {
      console.log("Email sent");
    } else {
      console.error("Failed to send email");
    }
  };

  return (
    <div className={styles.container}>
      <Grid container direction={"column"} gap={2}>
        <p className={styles.title}>Account Recovery</p>
        <input
          className={styles.input}
          placeholder="Please Enter Your Email"
          value={email}
          onChange={handleInputChange}
        />
        <button className={styles.button} onClick={handleClick}>
          Get Link
        </button>
        <div className={styles.linkContainer}>
          <p>Back to </p>
          <Link href="/login" className={styles.linkText}>
            Login
          </Link>
          <span style={{ marginLeft: 8 }}>|</span>
          <Link href="/signup" className={styles.linkText}>
            Signup
          </Link>
        </div>
      </Grid>
    </div>
  );
};
