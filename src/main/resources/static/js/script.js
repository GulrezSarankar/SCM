console.log("Script loaded");

// Get the current theme from local storage
let currentTheme = getTheme();

// Initial theme setup
document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

// Function to change the theme
function changeTheme() {
  // Set the initial theme on the web page
  changePageTheme(currentTheme, "");

  // Set the listener to change theme button
  const changeThemeButton = document.querySelector("#theme_change_button");

  changeThemeButton.addEventListener("click", (event) => {
    let oldTheme = currentTheme;
    console.log("Change theme button clicked");

    // Toggle the theme
    currentTheme = currentTheme === "dark" ? "light" : "dark";
    console.log(currentTheme);

    // Change the page theme
    changePageTheme(currentTheme, oldTheme);
  });
}

// Set theme to local storage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

// Get theme from local storage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light"; // Default to "light" if no theme is set
}

// Change current page theme
function changePageTheme(theme, oldTheme) {
  // Update local storage with the current theme
  setTheme(theme);

  // Remove the old theme
  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  }

  // Set the current theme
  document.querySelector("html").classList.add(theme);

  // Change the text of the button
  document
    .querySelector("#theme_change_button")
    .querySelector("span").textContent = theme === "light" ? "Dark" : "Light";
}