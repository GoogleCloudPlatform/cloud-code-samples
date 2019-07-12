from django.db import models

# Create your models here.
class Message(models.Model):
    author = models.CharField(max_length=250)
    slug = models.SlugField(max_length=250)
    date = models.DateTimeField(auto_now_add=True)
    content = models.TextField(blank=True)

    class Meta:
        ordering = ('author',)
        verbose_name = 'message'
        verbose_name_plural = 'messages'

    def __str__(self):
        return '{}'.format(self.content)
