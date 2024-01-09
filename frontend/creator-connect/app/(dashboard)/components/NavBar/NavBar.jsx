"use client";

import * as React from "react";
import { useRouter } from "next/navigation";
import {
  Avatar,
  AppBar,
  Box,
  Toolbar,
  Typography,
  IconButton,
  Menu,
  Tooltip,
  MenuItem,
} from "@mui/material";

export default function ButtonAppBar() {
  const [anchorElUser, setAnchorElUser] = React.useState(null);
  const settings = ["Account", "Logout"];
  const router = useRouter();

  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };
  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleClick = (setting) => {
    if (setting === "Logout") {
      if (typeof window !== "undefined") {
        localStorage.clear();
      }
      router.replace("/login");
    }
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static" sx={{ backgroundColor: "#D9D9D9" }}>
        <Toolbar>
          <Typography
            variant="h4"
            sx={{
              flexGrow: 1,
              fontWeight: 600,
              color: "#222AEF",
            }}
          >
            CreatorConnect
          </Typography>
          <Tooltip title="Open settings">
            <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
              <Avatar
                alt="CreatorConnect"
                sx={{
                  bgcolor: "#222AEF",
                  color: "#D9D9D9",
                  height: 45,
                  width: 45,
                }}
              >
                C
              </Avatar>
            </IconButton>
          </Tooltip>
          <Menu
            sx={{ mt: "45px" }}
            id="menu-appbar"
            anchorEl={anchorElUser}
            anchorOrigin={{
              vertical: "top",
              horizontal: "right",
            }}
            keepMounted
            transformOrigin={{
              vertical: "top",
              horizontal: "right",
            }}
            open={Boolean(anchorElUser)}
            onClose={handleCloseUserMenu}
          >
            {settings.map((setting) => (
              <MenuItem key={setting} onClick={handleCloseUserMenu}>
                <Typography
                  textAlign="center"
                  onClick={() => handleClick(setting)}
                >
                  {setting}
                </Typography>
              </MenuItem>
            ))}
          </Menu>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
