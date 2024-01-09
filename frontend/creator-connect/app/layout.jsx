export const metadata = {
  title: "Creator Connect",
  description: "Find your connection",
};
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body style={{ margin: 0 }}>{children}</body>
      <ToastContainer />
    </html>
  );
}
