function isToday(date) {

  // 本日の日付
  const today = new Date();

  // 比較する日付を表示
  console.log(date.toDateString());

  // 本日と同じであれば「true」を返す
  if (today.toDateString() === date.toDateString()) return true;

  return false;

}