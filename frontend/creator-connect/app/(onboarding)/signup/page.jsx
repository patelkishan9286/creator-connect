import "../../../src/styles/globals.css";
import React, { Suspense } from "react";
import styles from "./signup.module.css";
import { SignupBox } from "../components/SignupBox/SignupBox";

const SignupPage = () => {
  return (
    <div className={styles.container}>
      <div className={styles.heading}>
        <p>Creator Connect</p>
      </div>
      <div className={styles.content}>
        <SignupBox />
      </div>
    </div>
  );
};

export default SignupPage;
