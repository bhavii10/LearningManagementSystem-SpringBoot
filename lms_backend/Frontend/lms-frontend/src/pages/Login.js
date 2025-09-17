import React, { useState } from "react";
import { loginUser } from "../services/api";
import { useNavigate } from "react-router-dom";
import "./auth.css";

export default function Login() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: "", password: "" });
  const [msg, setMsg] = useState("");

  const onChange = (e) => {
    setForm((f) => ({ ...f, [e.target.name]: e.target.value }));
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    setMsg("");
    try {
      const res = await loginUser(form);
      localStorage.setItem("user", JSON.stringify(res.data)); // store logged-in user
      navigate("/");
    } catch (err) {
      const m = err?.response?.data?.message || "Invalid email or password";
      setMsg(m);
    }
  };

  return (
    <div className="auth-wrapper">
      <form className="auth-card" onSubmit={onSubmit}>
        <h2>Welcome back</h2>

        <label>Email</label>
        <input type="email" name="email" value={form.email} onChange={onChange} required />

        <label>Password</label>
        <input type="password" name="password" value={form.password} onChange={onChange} required />

        <button type="submit">Login</button>
        <p className="hint">
          New here? <a href="/register">Create an account</a>
        </p>
        {msg && <div className="msg">{msg}</div>}
      </form>
    </div>
  );
}
//import React, { useState, useEffect } from "react";
//import { loginUser } from "../services/api";
//import { useNavigate } from "react-router-dom";
//import "./auth.css";
//
//export default function Login() {
//  const navigate = useNavigate();
//  const [form, setForm] = useState({ email: "", password: "" });
//  const [msg, setMsg] = useState("");
//
//  const onChange = (e) => {
//    setForm((f) => ({ ...f, [e.target.name]: e.target.value }));
//  };
//
//  const onSubmit = async (e) => {
//    e.preventDefault();
//    setMsg("");
//    try {
//      const res = await loginUser(form);
//      localStorage.setItem("user", JSON.stringify(res.data));
//      navigate("/", { replace: true });
//    } catch (err) {
//      const m = err?.response?.data?.message || "Invalid email or password";
//      setMsg(m);
//    }
//  };
//
//  const handleGoogleLogin = () => {
//    // Redirect to Spring Boot backend OAuth2 login
//    window.location.href = "http://localhost:8080/oauth2/authorization/google";
//  };
//
//  useEffect(() => {
//    const params = new URLSearchParams(window.location.search);
//    const token = params.get("token");
//    if (token) {
//      localStorage.setItem("token", token);
//      navigate("/admin/new", { replace: true });  // ✅ go to admin page
//    }
//
//  }, [navigate]);
//
//  return (
//    <div className="auth-wrapper">
//      <form className="auth-card" onSubmit={onSubmit}>
//        <h2>Welcome back</h2>
//
//        <label>Email</label>
//        <input
//          type="email"
//          name="email"
//          value={form.email}
//          onChange={onChange}
//          required
//        />
//
//        <label>Password</label>
//        <input
//          type="password"
//          name="password"
//          value={form.password}
//          onChange={onChange}
//          required
//        />
//
//        <button type="submit">Login</button>
//
//        <div style={{ margin: "15px 0", textAlign: "center" }}>
//          <span>OR</span>
//        </div>
//
//        <button type="button" onClick={handleGoogleLogin} className="google-btn">
//          Sign in with Google
//        </button>
//
//        <p className="hint">
//          New here? <a href="/register">Create an account</a>
//        </p>
//
//        {msg && <div className="msg">{msg}</div>}
//      </form>
//    </div>
//  );
//}
