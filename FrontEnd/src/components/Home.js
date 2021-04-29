
import React, { useEffect, useState } from "react";

export default function Home() {
  const  [news, setNews] = useState("")




  function handleClick(e) {
    e.preventDefault();
    setNews("Vi har ingen nyheder")
  }


  return (

      <div> 
      <h1> Nyheder </h1>

      
        <a href="#" onClick={handleClick}> Tryk på mig for at få nyheder </a>
    
        <p> {news} </p>

      </div>
    );
  
    }
  