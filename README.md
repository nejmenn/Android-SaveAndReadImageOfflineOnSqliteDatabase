# Android-SaveAndReadImageOfflineOnSqliteDatabase


Here is an example of load / save bitmap / byte [] and storing it without SQLite database

I'm get images from drawable and transform in Bitmap and finally convert to byte[]


Note:
1 - compress above 50% generates exception in sqlite, because it can only store / read up to 1mb per table row.
2 - loading many images into a listview can generate OutOfMemoryException.



![Alt text](http://i.imgur.com/mW1vFnx.png "Image Preview")
