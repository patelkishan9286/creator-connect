"use client";
import { Grid } from "@mui/material";
import Link from "next/link";
import React from "react";
import styles from "./LoginBox.module.css";
import { useLoginBox } from "./useLoginBox";

export const LoginBox = () => {
  const { email, setEmail, password, setPassword, handleSignin } =
    useLoginBox();

  return (
    <div className={styles.container}>
      <Grid container direction={"column"} gap={2}>
        <p className={styles.title}>Login</p>
        <input
          className={styles.input}
          placeholder="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className={styles.input}
          placeholder="password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className={styles.button} onClick={handleSignin}>
          Login
        </button>
        <div className={styles.linkContainer}>
          <p>New here?</p>
          <Link href="/signup" className={styles.linkText}>
            Signup
          </Link>
        </div>
      </Grid>
    </div>
  );
};
