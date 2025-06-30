console.log("this is script js")

function toggleSidebar() {
		const sidebar = document.querySelector(".sidebar");

		if (window.innerWidth <= 600) {
			// For small screens: toggle display
			if (sidebar.style.display === "block") {
				sidebar.style.display = "none";
			} else {
				sidebar.style.display = "block";
			}
		} else {
			// For desktop: toggle class to collapse/expand
			sidebar.classList.toggle("collapsed");
			const content = document.querySelector(".content");
			content.classList.toggle("expanded");
		}
	}