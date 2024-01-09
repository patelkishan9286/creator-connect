import { useRouter } from "next/navigation";
import { useState } from "react";
import { toast } from "react-toastify";

export const useInfluencerProfileForm = () => {
  const router = useRouter();
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [bio, setBio] = useState("");
  const [gender, setGender] = useState("");
  const [region, setRegion] = useState("");
  const [birthdate, setBirthdate] = useState("");
  const [token, setToken] = useState(null);

  const handleNext = () => {
    if (firstName && lastName && bio && gender && region && birthdate) {
      const influencerProfileData = {
        firstName,
        lastName,
        bio,
        gender,
        region,
        birthdate,
        token,
      };
      if (typeof window !== "undefined") {
        localStorage.setItem(
          "influencerProfileData",
          JSON.stringify(influencerProfileData)
        );
      }
      router.push("onboarding-influencer-2");
    } else {
      toast.error("Please fill out all fields");
    }
  };

  console.log("Inside inee funtion");
  return {
    firstName,
    setFirstName,
    lastName,
    setLastName,
    bio,
    setBio,
    gender,
    setGender,
    region,
    setRegion,
    birthdate,
    setBirthdate,
    handleNext,
    token,
    setToken,
  };
};
