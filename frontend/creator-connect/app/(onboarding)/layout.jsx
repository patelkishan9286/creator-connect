import SidePicture from "./components/SidePicture/SidePicture";
import styles from "./layout.module.css";

export default function OnboardingLayout({ children }) {
  return (
    <div className={styles.onboardingLayout}>
      <SidePicture />
      {children}
    </div>
  );
}
