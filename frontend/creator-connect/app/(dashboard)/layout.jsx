import NavBar from "./components/NavBar/NavBar";
export default function DashboardLayout({ children }) {
  return (
    <div style={{ backgroundColor: "#E8EAE0" }}>
      <NavBar />
      {children}
    </div>
  );
}
