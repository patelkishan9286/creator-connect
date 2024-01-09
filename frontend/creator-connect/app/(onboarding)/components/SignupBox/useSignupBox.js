import { useRouter } from "next/navigation";
const { useState } = require("react");
import { toast } from "react-toastify";

export const useSignupBox = () => {
  const [accountType, setAccountType] = useState("Influencer");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();

  const handleSignup = async () => {
    const signupData = {
      email,
      password,
      user_type: accountType,
    };

    try {
      const res = await fetch("http://localhost:8080/api/users/register", {
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
        body: JSON.stringify(signupData), // body data type must match "Content-Type" header
      });

      if (!res?.error) {
        toast.success("Sucessfully registered");
        router.push("login");
        return res;
      }
    } catch (error) {
      // console.log("signup error");
      toast.error("Error signing up", e);
    }
  };

  return {
    accountType,
    setAccountType,
    email,
    setEmail,
    password,
    setPassword,
    handleSignup,
  };
};
