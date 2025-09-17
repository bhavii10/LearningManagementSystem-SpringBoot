import React, { useState } from "react";
import { registerUser } from "../services/api";
import { useNavigate } from "react-router-dom";
import "./auth.css";

export default function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: "", email: "", password: "", role: "USER",
  });
  const [msg, setMsg] = useState("");

  const onChange = (e) => {
    setForm((f) => ({ ...f, [e.target.name]: e.target.value }));
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    setMsg("");
    try {
      const res = await registerUser(form);
      localStorage.setItem("user", JSON.stringify(res.data));
      setMsg("Registered successfully!");
      navigate("/login");
    } catch (err) {
      const m = err?.response?.data?.message || "Registration failed";
      setMsg(m);
    }
  };

  return (
    <div className="auth-wrapper">
      <form className="auth-card" onSubmit={onSubmit}>
        <h2>Create account</h2>

        <label>Name</label>
        <input name="name" value={form.name} onChange={onChange} required />

        <label>Email</label>
        <input type="email" name="email" value={form.email} onChange={onChange} required />

        <label>Password</label>
        <input type="password" name="password" value={form.password} onChange={onChange} required />

        <label>Role</label>
        <select name="role" value={form.role} onChange={onChange}>
          <option value="USER">USER</option>
          <option value="ADMIN">ADMIN</option>
        </select>

        <button type="submit">Register</button>

        <p className="hint">
          Already have an account? <a href="/login">Login</a>
        </p>
        {msg && <div className="msg">{msg}</div>}
      </form>
    </div>
  );
}