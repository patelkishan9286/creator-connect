import "../../../src/styles/globals.css";
import React from "react";
import styles from "./resetPassword.module.css";
import { ResetPasswordBox } from "../components/ResetPasswordBox/ResetPasswordBox";

const ResetPassword = () => {
  return (
    <div className={styles.container}>
      <div className={styles.heading}>
        <p>Creator Connect</p>
      </div>
      <div className={styles.content}>
        <ResetPasswordBox />
      </div>
    </div>
  );
};

export default ResetPassword;
