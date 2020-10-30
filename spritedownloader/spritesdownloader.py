import requests
import json
import os

class SpritesDownloader:

	OUT_DIR = "./"
	SPRITES_SOURCE = "http://dspr.malikov.us/"
	SPRITES_FILE_LIST = "http://dspr.malikov.us/filelist.php"

	def download_file_list(self):
		print("Downloading file list...")
		response = requests.get(self.SPRITES_FILE_LIST)
		if response.status_code == 200:
			file_list = json.loads(response.text)
			count = len(file_list)
			counter = 1
			for item in file_list:
				print("Downloading {} ({} of {})... ".format(item, counter, count))
				self._download_sprite(item)
				counter = counter + 1
		else:
			print("Failed to download file list, code: {}".format(response.status_code))

	def _download_sprite(self, sprite):
		response = requests.get("{}{}".format(self.SPRITES_SOURCE, sprite))
		if response.status_code == 200:
			if not os.path.exists(os.path.dirname(sprite)):
				os.makedirs(os.path.dirname(sprite))
			with open(sprite.replace("./", ""), "wb") as file_handle:
				for chunk in response.iter_content():
					file_handle.write(chunk)
		else:
			print("Failed to download {}, code: {}".format(sprite, response.status_code))

if __name__ == "__main__":
	SpritesDownloader().download_file_list()	
