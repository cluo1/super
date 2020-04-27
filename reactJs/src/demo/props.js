import React from 'react';
import { Player } from 'video-react';
// import "node_modules/video-react/dist/video-react.css"; 
// import "~video-react/styles/scss/video-react";
// <link rel="stylesheet" href="/css/video-react.css" />

export default props => {
  return (
    <Player
      playsInline
      poster="/assets/poster.png"
      src="https://media.w3.org/2010/05/sintel/trailer_hd.mp4"
    />
  );
};