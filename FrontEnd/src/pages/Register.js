import { useEffect, useState } from "react";
import { Redirect } from "react-router";
import Wrap from "../components/Wrap";
import AuthService from "../services/AuthService";
import CommonPasswordService from "../services/CommonPasswordService";
import TokenService from "../services/TokenService";
import UserService from "../services/UserService";

const initialData = {
    username: '',
    email: '',
    password: '',
}

const authService = new AuthService()
const tokenService = new TokenService()
const userService = new UserService()
const commonPasswordService = new CommonPasswordService()



// Den tjekker at det som du har tastet ind er en gyldig email. Dette kan dog bypasses, bedste løsning ville være at sende en email til personen.
function validateEmail(email) {
    const re = /^[a-åA-Å-0-9@.]*$/;
    return re.test(String(email).toLowerCase());
}

// Den gør så du kun kan bruge .!@#$%^&*()_+-= og A-Å, og gør at dit username skal være mindst 12 tegn, also no spaces
function validateUsername(username) {
    const regex = /^[a-åA-Å0-9!@#\$%\^\&*\)\(+=._-]{6,}$/;
    return regex.test(String(username).toLowerCase());
}

export default function Register(props) {
    const [data, setData] = useState(initialData)
    const [error, setError] = useState("")
    const [isRegistered, setIsRegistered] = useState(false)
    const [words, setWords] = useState([])
    const [isPasswordHidden, setPasswordHidden] = useState(true)

    const isDisabled = data.password.length < 12 || data.password.length > 128 || !validateEmail(data.email) || !validateUsername(data.username) || data.email.length <= 0

    useEffect(async() => {
        const words = await commonPasswordService.fetchCommonPasswords()

        setWords(words)
    }, [])

    const setFormData = (key) => {
        return (event) => {
        
            setData({
                ...data,
                [key]: event.currentTarget.value,
            })
        }
    }

    const onButtonClick = async () => {
        try {
            if (words.includes(data.password)){
                return setError("Your password seems to be too weak, try a unique one")
            }

            await userService.register(data)
            await authService.login(data.username, data.password)

            props.updateUser()

            setIsRegistered(true)
        } catch (_) {
            setError("Could not create user. Perhaps the username or email is already in use")
        }
    }

    if (isRegistered || tokenService.hasToken()) {
        return <Redirect to='/user'/>
    }

    const onChange = () => {
        setPasswordHidden(!isPasswordHidden)
      }

    return Wrap(
        <div>
            <label>
                <p>Username:</p>
                <input value={ data.username }  placeholder="Must be 6 or more characters" onChange={ setFormData("username") } type="text" name="name"/>
            </label>
            <label>
                <p>Password:</p>
                <input value={ data.password } placeholder="Must be 12 or more characters" onChange={ setFormData("password") } type={ isPasswordHidden ? 'password' : 'text' }/>
                <button onClick={ onChange }>{ isPasswordHidden ? 'Show' : 'Hide' } password</button>
            </label>
            <label>
                <p>Email:</p>
                <input value={ data.email } onChange={ setFormData("email") } placeholder="Insert e-mail here" type="text" name="email"/>
            </label>
           
            <button onClick={ onButtonClick } disabled={ isDisabled }>
                Register
            </button>
            { error.length > 0 && <p className="error">{ error }</p> }
        </div>
    )
}