import React, { useEffect } from "react";

function GoogleLoginCustom({ onLogin }) {

  function handleCredentialResponse(response) {
    const token = response.credential;
    console.log("ID Token:", token);
    onLogin(token);
  }

  useEffect(() => {
    if (window.google && window.google.accounts && window.google.accounts.id) {
      window.google.accounts.id.initialize({
        client_id: "125055213237-a87mu47e3u71vicmps3bkvt1lqpt5hie.apps.googleusercontent.com",
        callback: handleCredentialResponse,
        auto_select: false,
      });
    } else {
      console.warn("Google Identity Services not loaded yet");
    }
  }, []);

  const handleClick = () => {
    if (window.google && window.google.accounts && window.google.accounts.id) {
      window.google.accounts.id.prompt();
    } else {
      console.error("Google Identity Services not available");
    }
  };

  return (
    <button className="nes-btn is-primary" onClick={handleClick}>
      <i className="nes-icon google is-small" /> Sign in with Google
    </button>
  );
}

export default GoogleLoginCustom;
