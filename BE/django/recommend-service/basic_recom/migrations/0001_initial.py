# Generated by Django 3.2.13 on 2023-03-31 01:11

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Author',
            fields=[
                ('author_id', models.BigAutoField(primary_key=True, serialize=False)),
                ('author_name', models.CharField(max_length=32)),
            ],
            options={
                'db_table': 'author',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Book',
            fields=[
                ('book_isbn', models.CharField(max_length=16, primary_key=True, serialize=False)),
                ('book_title', models.CharField(max_length=256)),
                ('book_publisher', models.CharField(max_length=50)),
                ('book_price', models.IntegerField()),
                ('book_description', models.TextField()),
                ('book_grade', models.TextField(blank=True, null=True)),
                ('book_image', models.TextField(blank=True, null=True)),
            ],
            options={
                'db_table': 'book',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='BookAuthor',
            fields=[
                ('book_author_id', models.BigAutoField(db_column='book_author_id', primary_key=True, serialize=False)),
            ],
            options={
                'db_table': 'book_author',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='BookGenre',
            fields=[
                ('book_genre_id', models.BigAutoField(primary_key=True, serialize=False)),
            ],
            options={
                'db_table': 'book_genre',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='BookLikes',
            fields=[
                ('book_like_id', models.BigAutoField(primary_key=True, serialize=False)),
                ('book_id', models.BigIntegerField()),
                ('user_id', models.BigIntegerField()),
            ],
            options={
                'db_table': 'book_likes',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Genre',
            fields=[
                ('genre_id', models.BigAutoField(primary_key=True, serialize=False)),
                ('genre_name', models.CharField(max_length=32)),
            ],
            options={
                'db_table': 'genre',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Review',
            fields=[
                ('review_id', models.BigAutoField(primary_key=True, serialize=False)),
                ('review_content', models.CharField(blank=True, max_length=255, null=True)),
                ('review_grade', models.FloatField()),
                ('user_id', models.BigIntegerField()),
                ('book_isbn', models.CharField(max_length=32)),
                ('created_date', models.DateTimeField(blank=True, null=True)),
                ('modified_date', models.DateTimeField(blank=True, null=True)),
            ],
            options={
                'db_table': 'review',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Userr',
            fields=[
                ('user_id', models.BigAutoField(primary_key=True, serialize=False)),
                ('created_at', models.DateTimeField()),
                ('email', models.CharField(max_length=64, unique=True)),
                ('username', models.CharField(max_length=32, unique=True)),
                ('nickname', models.CharField(max_length=32, unique=True)),
                ('password', models.CharField(max_length=128)),
                ('user_type', models.IntegerField(blank=True, null=True)),
                ('user_role', models.IntegerField(blank=True, null=True)),
                ('age', models.IntegerField()),
                ('sex', models.IntegerField()),
                ('prefer_score', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'user',
                'managed': False,
            },
        ),
    ]