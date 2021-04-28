
import React, { useEffect, useState } from "react";

export default function Home() {
  const [breed, setbreed] = useState("")
    function fetchBreed() {
        fetch("http://localhost:8080/jpareststarter/api/dog/breeds")
            .then(res => res.json())
            .then(data => {
                setbreed(data)
            })
    }
    useEffect(() => {
        fetchBreed()
    }, [])

    if(breed.dogs){
      return (
        <div>
            <h2>Breed List</h2>
            <ul>{breed.dogs.map((dog) =>{
            return(

              <li>
                {dog.breed}
              </li>
            );
            })}</ul>
            
               
            
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
  
    