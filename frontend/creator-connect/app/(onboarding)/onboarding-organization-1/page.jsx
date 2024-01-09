import React from "react";
import "../../../src/styles/globals.css";
import { OrganizationProfileForm } from "../components/OrganizationProfileForm/OrganizationProfileForm";
import styles from "./OnboardingOrganizationFirstStep.module.css";

const OnboardingOrganizationFirstStep = () => {
  return (
    <div className={styles.container}>
      <div className={styles.heading}>
        <p>Create your organization profile</p>
      </div>
      <div className={styles.content}>
        <OrganizationProfileForm />
      </div>
    </div>
  );
};

export default OnboardingOrganizationFirstStep;
