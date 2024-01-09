import { useRouter } from "next/navigation";
import { useState } from "react";
import { toast } from "react-toastify";

export const useOrganizationProfileForm = () => {
  const router = useRouter();
  const [organizationName, setOrganizationName] = useState("");
  const [description, setDescription] = useState("");
  const [websiteLink, setWebsiteLink] = useState("");
  const [region, setRegion] = useState("");
  const [organizationSize, setOrganizationSize] = useState("");

  const handleNext = () => {
    if (
      organizationName &&
      description &&
      websiteLink &&
      region &&
      organizationSize
    ) {
      const organizationProfileData = {
        organizationName,
        description,
        websiteLink,
        region,
        organizationSize,
      };
      if (typeof window !== "undefined") {
        localStorage.setItem(
          "organizationProfileData",
          JSON.stringify(organizationProfileData)
        );
      }

      router.push("onboarding-organization-2");
    } else {
      toast.error("Please fill out all fields");
    }
  };

  return {
    organizationName,
    setOrganizationName,
    description,
    setDescription,
    websiteLink,
    setWebsiteLink,
    region,
    setRegion,
    organizationSize,
    setOrganizationSize,
    handleNext,
  };
};
