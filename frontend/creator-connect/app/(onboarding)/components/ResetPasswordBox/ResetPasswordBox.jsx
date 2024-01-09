"use client";
import { Grid } from "@mui/material";
import Link from "next/link";
import React from "react";
import styles from "./ResetPasswordBox.module.css";

export const ResetPasswordBox = () => {
  return (
    <div className={styles.container}>
      <Grid container direction={"column"} gap={2}>
        <p className={styles.title}>Reset Password</p>
        <input className={styles.input} placeholder="Your Token" />
        <input className={styles.input} placeholder="Enter Your New Password" />
        <button className={styles.button}>Reset</button>
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
