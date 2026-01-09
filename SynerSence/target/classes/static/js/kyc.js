const video = document.getElementById("video");
const canvas = document.getElementById("canvas");
const photo = document.getElementById("photo");

navigator.mediaDevices.getUserMedia({ video: true })
  .then(stream => video.srcObject = stream);

function capture() {
  canvas.width = video.videoWidth;
  canvas.height = video.videoHeight;
  canvas.getContext("2d").drawImage(video, 0, 0);
  photo.src = canvas.toDataURL("image/png");
}

function save() {
  const patientId = document.getElementById("patientId").value;

  fetch("/kyc/save", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({
      patientId: patientId,
      imageBase64: photo.src
    })
  })
  .then(res => res.text())
  .then(alert);
}
