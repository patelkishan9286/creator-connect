"use client";
import { useState } from "react";
import { toast } from "react-toastify";
import { useRouter } from "next/navigation";

export const useInfluencerDetailsForm = () => {
  const router = useRouter();
  const influencerNicheList = [
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

  const [selectedNiches, setSelectedNiches] = useState(influencerNicheList);
  const [instagramUrl, setInstagramUrl] = useState("");
  const [twitterUrl, setTwitterUrl] = useState("");
  const [youtubeUrl, setYoutubeUrl] = useState("");
  const [facebookUrl, setFacebookUrl] = useState("");
  const [minimumRate, setMinimumRate] = useState("");

  const handleFinish = async () => {
    if (getSelectedNicheNames(selectedNiches).length && minimumRate) {
      let influencerProfileDataString;
      let userData;
      let influencerProfileData;
      let token;
      if (typeof window !== "undefined") {
        influencerProfileDataString = localStorage.getItem(
          "influencerProfileData"
        );
        influencerProfileData = JSON.parse(influencerProfileDataString);
        userData = localStorage.getItem("userData");
        token = localStorage.getItem("token");
      }

      const influencerOnboardingInfo = {
        name: `${influencerProfileData?.firstName} ${influencerProfileData.lastName}`,
        gender: influencerProfileData.gender,
        influencerName: `${influencerProfileData?.firstName} ${influencerProfileData.lastName}`,
        influencerType: "asdf", //not needed
        influencerNiche: getSelectedNicheNames(selectedNiches),
        minRate: parseInt(minimumRate),
        location: influencerProfileData?.region,
        bio: influencerProfileData?.bio,
        previousBrands: "asdf",
        profileImage: "asdf",
        birthdate: influencerProfileData?.birthdate,
        instagram: instagramUrl,
        tweeter: twitterUrl,
        youtube: youtubeUrl,
        facebook: facebookUrl,
        bestPosts: ["asdf", "asdf"],
        tiktok: "",
        twitch: "",
      };
      try {
        const res = await fetch(
          `http://localhost:8080/api/influencers/register/${userData?.userID}`,
          {
            method: "POST", // *GET, POST, PUT, DELETE, etc.
            mode: "cors", // no-cors, *cors, same-origin
            // cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            // credentials: "same-origin", // include, *same-origin, omit
            headers: {
              "Content-Type": "application/json",
              // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            // redirect: "follow", // manual, *follow, error
            // referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin,
            body: JSON.stringify(influencerOnboardingInfo), // body data type must match "Content-Type" header
          }
        );
        if (!res.error) router.push("influencer-dashboard");
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
    influencerNicheList,
    instagramUrl,
    setInstagramUrl,
    twitterUrl,
    setTwitterUrl,
    youtubeUrl,
    setYoutubeUrl,
    facebookUrl,
    setFacebookUrl,
    handleSelect,
    selectedNiches,
    setSelectedNiches,
    minimumRate,
    setMinimumRate,
    handleFinish,
  };
};
