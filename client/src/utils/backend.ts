const fetchGpx = async function() {
  const response = await fetch("http://localhost:3000/route");
  const data = await response.json();

  return data.results;
};

function convert(str: string): string {
  const date = new Date(str),
      month = ("0" + (date.getMonth() + 1)).slice(-2),
      day = ("0" + date.getDate()).slice(-2);
  return [date.getFullYear(), month, day].join("-");
}

const fetchGpxBetweenDates = async function(fromDate: string, toDate: string) {
  fromDate = convert(fromDate);
  toDate = convert(toDate);
  const response = await fetch(`http://localhost:3000/route/date?fromDate=${fromDate}&toDate=${toDate}`);
  const data = await response.json();

  return data.results;
}

export { fetchGpx, fetchGpxBetweenDates };
