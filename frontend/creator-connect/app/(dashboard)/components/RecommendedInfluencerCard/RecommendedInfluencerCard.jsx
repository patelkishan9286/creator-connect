import { styled } from "@mui/material/styles";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { Container, Chip } from "@mui/material";
import SocialMediaIcons from "../SocialMediaIcons/SocialMediaIcons";
import { useRouter } from "next/navigation";

const Img = styled("img")({
  margin: "auto",
  display: "block",
  width: "100%",
  height: "100%",
  objectFit: "cover",
});

export default function RecommendedInfluencerCard({ influencer }) {
  const router = useRouter();

  const navigateToInfluencerProfile = () => {
    router.push(`/influencer-profile/${influencer.influencerID}`);
  };

  const {
    name,
    instagram,
    tikTok,
    tweeter,
    youtube,
    facebook,
    twitch,
    influencerNiche,
  } = influencer;
  return (
    <Paper
      onClick={navigateToInfluencerProfile}
      sx={{
        margin: "auto",
        maxWidth: 325,
        flexGrow: 1,
        backgroundColor: "#D9D9D9",
        textTransform: "none",
        cursor: "pointer",
      }}
    >
      <Grid container direction="column">
        <Grid item sx={{ height: "150px" }}>
          <Img
            alt="profileImage"
            src="https://images.pexels.com/photos/8691641/pexels-photo-8691641.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
          />
        </Grid>
        <Container>
          <Grid container direction="column">
            <Grid item xs={12} mt={1}>
              <Typography variant="h6" sx={{ fontWeight: "600" }}>
                {name}
              </Typography>
            </Grid>
            <Grid item xs={12}>
              <div onClick={(e) => e.stopPropagation()}>
                <SocialMediaIcons
                  links={{
                    instagram,
                    tweeter,
                    tikTok,
                    youtube,
                    facebook,
                    twitch,
                  }}
                />
              </div>
            </Grid>
            <Grid item xs={12}>
              <Typography
                variant="body2"
                color="text.secondary"
                fontWeight={600}
              >
                {influencerNiche.map((niche) => (
                  <Chip
                    key={niche}
                    label={niche}
                    sx={{ ml: 1, mb: 1, backgroundColor: "#E8EaE0" }}
                  />
                ))}
              </Typography>
            </Grid>
          </Grid>
        </Container>
      </Grid>
    </Paper>
  );
}
