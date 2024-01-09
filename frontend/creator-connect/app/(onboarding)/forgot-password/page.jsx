import "../../../src/styles/globals.css";
import React from "react";
import styles from "./forgot-password.module.css";
import { ForgotPasswordBox } from "../components/ForgotPasswordBox/ForgotPasswordBox";

const ForgotPassword = () => {
  return (
    <div className={styles.container}>
      <div className={styles.heading}>
        <p>Creator Connect</p>
      </div>
      <div className={styles.content}>
        <ForgotPasswordBox />
      </div>
    </div>
  );
};

export default ForgotPassword;
