# Generated by Django 4.1.7 on 2023-03-16 08:28

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='BookInfoModel',
            fields=[
                ('book_id', models.AutoField(primary_key=True, serialize=False)),
                ('isdn', models.CharField(max_length=13)),
                ('imgsrc', models.CharField(max_length=300)),
                ('cartoon', models.FloatField(default=0)),
                ('solid', models.FloatField(default=0)),
                ('infographic', models.FloatField(default=0)),
                ('realistic', models.FloatField(default=0)),
                ('art', models.FloatField(default=0)),
            ],
            options={
                'db_table': 'bookinfo',
            },
        ),
    ]