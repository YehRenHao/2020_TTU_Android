# 技巧

## 建立一個自己的資料庫
``` JAVA
public class DBOpenHelper extends SQLiteOpenHelper {
    final static String _TableName = "friend";
    final static String ID_FIELD = "_id";
    final static String NAME_FIELD = "NAME";
    final static String SEXUAL_FIELD = "SEX";
    final static String ADDRESS_FIELD = "ADDRESS";
    final static String IMAGE_FIELD = "IMAGE";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + "( " +
                ID_FIELD + " integer PRIMARY KEY AUTOINCREMENT, " +
                NAME_FIELD + " text," +
                SEXUAL_FIELD + " text, " +
                ADDRESS_FIELD + " text, " +
                IMAGE_FIELD + " binary )";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + _TableName;
        db.execSQL(SQL);
    }
}
```
``` JAVA
// 增加資料
long add(String name, String sex, String address, byte[] image) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(NAME_FIELD, name);
    values.put(SEXUAL_FIELD, sex);
    values.put(ADDRESS_FIELD, address);
    values.put(IMAGE_FIELD, image);
    long result = db.insert(_TableName, null, values);
    
    return result;
}
```
``` JAVA
// 更新資料
long update(String name, String sexual, String address, byte[] image, String whereClause) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    if (name != null) values.put(NAME_FIELD, name);
    if (sexual != null) values.put(SEXUAL_FIELD, sexual);
    if (address != null) values.put(ADDRESS_FIELD, address);
    if (image != null) {
        values.put(IMAGE_FIELD, image);
        System.out.println("AFTER" + values);
    }

    long result = db.update(_TableName, values, whereClause, null);
    db.close();

    return result;
}
```
``` JAVA
// 刪除資料
int delete(String _id) {
    SQLiteDatabase db = this.getWritableDatabase();
    int result = db.delete(_TableName, ID_FIELD + " =" + _id, null);
    db.close();

    return result;
}
```
``` JAVA
//查詢所有資料
Cursor selectAll() {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = null;
    c = db.rawQuery("SELECT * FROM " + _TableName, null);

    return c;
}
```

``` JAVA
//查詢某筆資料
Cursor selectData(String id, String name, String sexual, String address) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = null;

    if (!id.equals("")){
        c = db.rawQuery("SELECT * FROM " + _TableName + " WHERE " + "_id='" + id + "'", null);
    }
    if (!name.equals("")) {
        c = db.rawQuery("SELECT * FROM " + _TableName + " WHERE " + "name='" + name + "'", null);
    } else if (!sexual.equals("")) {
        c = db.rawQuery("SELECT * FROM " + _TableName + " WHERE " + "sex = '" + sexual + "'", null);
    } else if (!address.equals("")) {
        c = db.rawQuery("SELECT * FROM " + _TableName + " WHERE " + "address = '" + address + "'", null);
    }

    return c;
}
```
``` JAVA
// 資料庫要上傳圖片，要轉換成byte[]型態
Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
ByteArrayOutputStream stream = new ByteArrayOutputStream();
bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
byte[] byteArray = stream.toByteArray();

access.update(
    editName.getText() + "",
    editSex.getText() + "",
    editAddress.getText() + "",
    byteArray,
    DBOpenHelper.ID_FIELD + " = " + id
);
```
## 選擇圖片
``` JAVA
// 選擇圖片，開一個新的視窗
Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
startActivityForResult(gallery, PICK_IMAGE);
```
``` JAVA
// 接收回來的圖片
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
        Uri imageUri = data.getData();
        image.setImageURI(imageUri);
    }
}
```
## 自定義ImageCursorAdapter
``` JAVA
public class ImageCursorAdapter extends CursorAdapter {

    public ImageCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.NAME_FIELD)));
        TextView sexual = (TextView) view.findViewById(R.id.sex);
        sexual.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.SEXUAL_FIELD)));
        TextView address = (TextView) view.findViewById(R.id.address);
        address.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.ADDRESS_FIELD)));
        byte[] imageByte = cursor.getBlob(cursor.getColumnIndex(DBOpenHelper.IMAGE_FIELD));
        ImageView image = (ImageView) view.findViewById(R.id.image);
        if (imageByte != null) {
            // 因為資料庫裡的圖片是byte[]格式，要轉檔回去
            image.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        }
    }
}
```
``` JAVA
// 在每次回轉到主頁面，顯示資料庫檔案
@Override
protected void onResume() {
    Cursor c = access.selectAll();
    if (adapter == null) {
        adapter = new ImageCursorAdapter(this, c, 0);
        list.setAdapter(adapter);
    } else {
        adapter.changeCursor(c);
    }
    super.onResume();
}
```