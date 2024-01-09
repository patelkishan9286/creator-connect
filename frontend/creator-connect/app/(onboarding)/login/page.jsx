"use client";
import "../../../src/styles/globals.css";
import React from "react";
import styles from "./login.module.css";
import { LoginBox } from "../components/LoginBox/LoginBox";

const LoginPage = () => {
  return (
    <div className={styles.container}>
      <div className={styles.heading}>
        <p>Creator Connect</p>
      </div>
      <div className={styles.content}>
        <LoginBox />
      </div>
    </div>
  );
};

export default LoginPage;
