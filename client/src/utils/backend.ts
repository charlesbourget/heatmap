const fetchGpx = async function() {
  const response = await fetch("http://localhost:3000/route");
  const data = await response.json();

  return data.results;
};

export { fetchGpx };
