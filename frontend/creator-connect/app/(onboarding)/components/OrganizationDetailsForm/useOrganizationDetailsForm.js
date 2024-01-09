import { useState } from "react";
import { toast } from "react-toastify";
import { useRouter } from "next/navigation";

export const useOrganizationDetailsForm = () => {
  const router = useRouter();
  const organizationNicheList = [
    {
      name: "Fashion",
      selected: false,
    },
    {
      name: "Beauty",
      selected: false,
    },
    {
      name: "Health",
      selected: false,
    },
    {
      name: "Celebrity",
      selected: false,
    },
    {
      name: "Video Game",
      selected: false,
    },
    {
      name: "Brand Ambassador",
      selected: false,
    },
    {
      name: "Wellness",
      selected: false,
    },
    {
      name: "Vlogger",
      selected: false,
    },
    {
      name: "Entrpreneurship",
      selected: false,
    },
    {
      name: "Cooking",
      selected: false,
    },
    {
      name: "Fitness",
      selected: false,
    },
  ];

  const [selectedNiches, setSelectedNiches] = useState(organizationNicheList);
  const [instagramUrl, setInstagramUrl] = useState("");
  const [twitterUrl, setTwitterUrl] = useState("");
  const [youtubeUrl, setYoutubeUrl] = useState("");
  const [facebookUrl, setFacebookUrl] = useState("");
  const [industry, setIndustry] = useState("");

  const handleFinish = async () => {
    if (getSelectedNicheNames(selectedNiches).length && industry) {
      let organizationProfileDataString;
      let userData;
      if (typeof window !== "undefined") {
        organizationProfileDataString = localStorage.getItem(
          "organizationProfileData"
        );

        userData = localStorage.getItem("userData");
      }

      userData = JSON.parse(userData);

      const organizationProfileData = JSON.parse(organizationProfileDataString);

      const organizationOnboardingInfo = {
        orgName: organizationProfileData?.organizationName,
        profileImage: "",
        companyType: industry,
        size: parseInt(organizationProfileData?.organizationSize),
        websiteLink: organizationProfileData?.websiteLink,
        targetInfluencerNiche: getSelectedNicheNames(selectedNiches),
        location: organizationProfileData?.region,
        bio: organizationProfileData?.description,
        previousBrands: "",
        instagram: instagramUrl,
        tweeter: twitterUrl,
        youtube: youtubeUrl,
        facebook: facebookUrl,
        tiktok: "",
        twitch: "",
      };

      try {
        const res = await fetch(
          `http://localhost:8080/api/organizations/register/${userData?.userID}`,
          {
            method: "POST", // *GET, POST, PUT, DELETE, etc.
            // mode: "cors", // no-cors, *cors, same-origin
            mode: "cors",
            // cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            // credentials: "same-origin", // include, *same-origin, omit
            headers: {
              "Content-Type": "application/json",
              // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            // redirect: "follow", // manual, *follow, error
            // referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin,
            body: JSON.stringify(organizationOnboardingInfo), // body data type must match "Content-Type" header
          }
        );

        if (!res.error) router.push("organization-dashboard");
        else toast.error(error);
        return res;
      } catch (error) {
        toast.error(error);
        console.log("Error", error);
      }
    } else {
      toast.error("Please fill out all fields, social URL's are optional.");
    }
  };
  const handleSelect = (data, setData, index) => {
    const newNiches = data.map((niche, i) => {
      if (index === i) {
        niche.selected = !niche.selected;
      }
      return niche;
    });

    setData(newNiches);
  };

  //filteres the selected data to return only the selected ones as an array of the fields.
  function getSelectedNicheNames(nicheList) {
    return nicheList
      .filter((niche) => niche.selected) // filter out unselected niches
      .map((niche) => niche.name); // transform remaining objects to just their names
  }

  return {
    organizationNicheList,
    handleSelect,
    selectedNiches,
    setSelectedNiches,
    instagramUrl,
    setInstagramUrl,
    twitterUrl,
    setTwitterUrl,
    youtubeUrl,
    setYoutubeUrl,
    facebookUrl,
    setFacebookUrl,
    industry,
    setIndustry,
    handleFinish,
  };
};
