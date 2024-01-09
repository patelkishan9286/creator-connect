import axios from "axios";

export default async (req, res) => {
  try {
    const response = await axios.post(
      "http://localhost:8080/api/users/forgot-password",
      req.body
    );
    res.status(200).json(response.data);
  } catch (error) {
    console.log("proxy errr");
    res.status(error.response.status).json(error.response.data);
  }
};
