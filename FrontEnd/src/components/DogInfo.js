
import { waitFor } from "@testing-library/react";
import React, { useState } from "react";

export default function DogInfo() {
  const [breed, setbreed] = useState("")
  const [query, setQuery] = useState("")
     
  const onChange = (e) => {
    const { value } = e.target;
    setQuery(value);
    console.log(query)
  };
  const onSubmit = (e) => {
    search(query)
    
    e.preventDefault();
  }
  const search = (e) => {
    const url = "http://localhost:8080/jpareststarter/api/dog/" + query;
    console.log(query)
    

    fetch(url)
    .then(res => res.json())
    .then(data => {
        setbreed(data)
    })
    setTimeout(10000000000)
  };
  console.log(breed)
  if(breed){
    return (
      <div>
      <form onSubmit = {onSubmit}>
        <input
          type="text"
          className="search-box"
          placeholder="Search for..."
          onChange={onChange}
        />
        <input type="submit" value="Submit"/>
        <ul >

          <li>{breed.img}</li>
          <li>{breed.breed}</li>
          <li>{breed.wikipedia}</li>
          <li>{breed.info}</li>
          <li>{breed.fact}</li>

  </ul>
      </form>
      </div>
    );
  }
  else{
    return(
    <form onSubmit = {onSubmit}>
      <input
        type="text"
        className="search-box"
        placeholder="Search for..."
        onChange={onChange}
      />
      <input type="submit" value="Submit"/>
    </form>
    );
  }
}

  
