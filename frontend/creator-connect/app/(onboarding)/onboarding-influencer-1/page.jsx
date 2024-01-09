import "../../../src/styles/globals.css";
import React from "react";
import styles from "./OnboardingInfluencerFirstStep.module.css";
import { InfluencerProfileForm } from "../components/InfluencerProfileForm/InfluencerProfileForm";

const OnboardingInfluencerFirstStep = () => {
  return (
    <div className={styles.container}>
      <div className={styles.heading}>
        <p>Create your profile</p>
      </div>
      <div className={styles.content}>
        <InfluencerProfileForm />
      </div>
    </div>
  );
};

export default OnboardingInfluencerFirstStep;
