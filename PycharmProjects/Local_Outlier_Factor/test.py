from __future__ import print_function
import matplotlib.pyplot as plt
import numpy as np

import ekg_data
import learn_utils
from lof import LOF

# Read data file
ekg_filename = 'a09.dat'
ekg_data = ekg_data.read_ekg_data(ekg_filename)
print(ekg_data.shape)

# Cutting a small piece of data
ekg_data = ekg_data[0:1024]

# Setting up window size and slide interval
segment_len = 2
slide_len = 2

# Training data segments
segments = []
for start_pos in range(0, len(ekg_data), slide_len):
    end_pos = start_pos + segment_len
    segment = np.copy(ekg_data[start_pos:end_pos]).tolist()
    if len(segment) != segment_len:
        continue
    segments.append(segment)
print("Produced %d waveform segments" % len(segments))

# LOF function call
lof = LOF(segments)

# Generating random test data
random_data = np.random.randint(-50, 50, 120)
sources = []
for start_pos in range(0, len(random_data), slide_len):
    end_pos = start_pos + segment_len
    source = np.copy(random_data[start_pos:end_pos]).tolist()
    if len(source) != segment_len:
        continue
    sources.append(source)
print("Produced %d waveform test_segments" % len(sources))


x,y = zip(*segments)
plt.scatter(x,y, 20, color="#0000FF")

for instance in sources:
    value = lof.local_outlier_factor(3, instance)
    color = "#FF0000" if value > 1 else "#00FF00"
    plt.scatter(instance[0], instance[1], color=color)


plt.show()