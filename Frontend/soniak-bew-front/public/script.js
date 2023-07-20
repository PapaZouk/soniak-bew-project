const form = document.querySelector("form");

form.addEventListener("submit", (event) => {
  event.preventDefault();
  const name = form.elements.name.value;
  const email = form.elements.name.email;
  const message = form.elements.name.message;

  alert(
    `Thank you for yoyr message, ${name}! We well get back to you at ${email} as soon as we can.`
  );
  form.reset();
});
