
import { useEffect, useState, Button, Form } from "react";

export default function Account({facade,activeUser}) {

  const[newDog, setNewDog] = useState({ name : "", dob : "", breed : "", info : "", activeUser : activeUser});
  const[status, setStatus] = useState("");
  
  function onChange(e){
    const target = e.target;
    const value = target.value;
    const name = target.id;
  
    setStatus("");
    
    setNewDog({
      ...newDog,
      [name]: value,

    });
  }
  function onSubmit(e){
    e.preventDefault();

    facade.registerDog(newDog)
    .then((data)=> {
      setStatus(data.msg);
      
    })
    .catch((err) =>{
      if (err.satus){
        err.fullError.then((e) => setStatus(e.message));
      }else{
        setStatus("Network error has occured: no dog was created");
        console.log("Network error! Could not create dog");
      }
    })
  }



  return (
     
    <div>

    <h1>Welcome to your page</h1> 

        <h2>Create A New Profile</h2>
   
          <h5>{status}</h5>

        <form onSubmit={onSubmit}>
              Dog Name:
              <input type="text" id="name" onChange={onChange} />
              Dog Breed:
              <input type="text" id="breed" onChange={onChange}  />
              Date of Birth:
              <input type="text" id="dob" onChange={onChange} />
              Info:
              <input type="text" id="info" onChange={onChange} />
              <input type="submit" value="Submit" />
          </form>
      </div>
  
);
  
 }