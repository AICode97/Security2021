import { act } from "@testing-library/react";
import React, { useEffect, useState } from "react";

export default function MyDogs({activeUser}) {
  const [breed, setbreed] = useState("")
    function fetchBreed() {
        fetch("http://localhost:8080/jpareststarter/api/dog/mydog/"+ activeUser)
            .then(res => res.json())
            .then(data => {
                setbreed(data)
            })
            setTimeout(100000000);
    }
    useEffect(() => {
        fetchBreed()
    }, [])
    console.log(activeUser);
    if(breed){
      return (
        <div>
            <h2>Breed List</h2>
            {breed.map((dog) =>{
            return(
                <ul>
              <li>Name: {dog.name}</li>
              <li>Date of birth: {dog.dateOfBirth}</li>
              <li>Information: {dog.info}</li>
              <li>Breed: {dog.breed}</li>
              </ul>
            );
            })}
            
               
            
        </div>
    )
}
else{
  return(
  <div>
  <h2>Breed List</h2>
  <p></p>   
</div>
  )}

    }
  
    