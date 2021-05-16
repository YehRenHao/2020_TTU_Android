# 技巧

## Activity之間傳送值
``` JAVA
// 自定義的物件類型需要implements Serializable才可以傳遞
class CustomData implements Serializable {
    String title;
    String subtitle;
    Integer image;
    public CustomData(String title, String subtitle, Integer image) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
    }
}
```
``` JAVA
// 傳遞資料，1st Activity
ArrayList<SubjectData> arrayList; // 要傳遞的資料
Intent intent = new Intent(MainActivity.this, PassTo.class);
intent.putExtra("DataName", arrayList); // 放入要傳遞的資料
startActivityForResult(intent, 1); // 需要有requestCode才可以傳回來
```
``` JAVA
// 傳回資料，2nd Activity
Intent intent = new Intent(PassTo.this, MainActivity.class);
intent.putExtra("DataName", arrayList); // 放入要傳回的資料
setResult(RESULT_OK, intent);
finish();
```
``` JAVA
// 收取資料，1st Activity
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent newIntent) {
    // 從另一個Activity傳回arrayList
    super.onActivityResult(requestCode, resultCode, newIntent);
    if (requestCode == 1) {
        if (resultCode == RESULT_OK) {
            arrayList = (ArrayList<SubjectData>)newIntent.getSerializableExtra("DataName");
        }
    }
}
```
## 自定義ArrayAdapter
``` JAVA
class MyListAdapter extends ArrayAdapter<CustomObject>{
    private ArrayList<CustomObject> arrayList;
    private Context context;
    public MyListAdapter(Context context, ArrayList<CustomObject> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CustomObject CustomObject = arrayList.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.mylist, null);
            TextView title = convertView.findViewById(R.id.title);
            TextView subtitle = convertView.findViewById(R.id.subtitle);
            ImageView image = convertView.findViewById(R.id.image);
            title.setText(CustomObject.name);
            subtitle.setText(CustomObject.birthday);
            image.setImageResource(CustomObject.image);
       }
        return convertView;
    }
}
```
## 選擇日期
``` JAVA
public void datePicker(View v){
    Calendar calendar = Calendar.getInstance();
    // 這邊要先get一次，不然會從1990開始，我不知道為什麼，請自己找。
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Calendar裡的月份從0開始，很鳥，所以要+1
            String dateTime = String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(day);
        }
    }, year, month, day).show();
}
```
## 從xml之中拿取資料
``` JAVA
getResources().getStringArray(R.array.array);
```
## 對話方塊
``` JAVA
AlertDialog.Builder builder = new AlertDialog.Builder(Activity.this);
builder.setTitle("Title");
builder.setIcon("Icon");
builder.setMessage("Message");
builder.setNegativeButton("離開", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int id) {
    }
});
AlertDialog dialog = builder.create();
dialog.setCancelable(false); // 設定沒按離開鍵不能返回
dialog.show();
```
## 發送泡泡訊息
``` JAVA
Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT).show();
```
## 偵測軟鍵盤
``` JAVA
EditText = (EditText)findViewById(R.id.EditText);
EditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    if(actionId == EditorInfo.IME_ACTION_SEND) { // 判斷是否是“SEND”鍵
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0); // 隱藏軟鍵盤
        }
    }
}
```