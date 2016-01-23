import os


msg = "{} a travaille :\t{} cette semaine en {} fois durant {} (en moyenne)"

def read_file(path):
    f = open(path, 'r')
    d = f.readlines()
    f.close()
    return d

def heure_conversion(HHhMM):
    h, m = map(int, HHhMM.split('h'))
    return 60 * h + m

def heure_format(mins):
    return str(mins // 60) + 'h' + str(mins % 60)

def get_work_time(lines):
    L = []
    for line in lines[1:]:
        start, end = map(heure_conversion, line.split(',')[:2])
        if end > start:
            L.append(end - start)
        else:
            L.append(24 * 60 - start + end)
    return L

def extract_user(lines):
    return lines[1].split(',')[5].replace(chr(10), '')

def get_week_dir(nb_wk, nb_mt):
    return "week_{}_{}".format(nb_wk, nb_mt)

week_path = get_week_dir(input("semaine : "), input("mois : "))

for f in os.listdir(week_path):
    if not week_path[4:] + ".csv" in f:
        continue
    f = read_file(week_path + '/' + f)
    u = extract_user(f)
    wt = get_work_time(f)
    print msg.format(u, heure_format(sum(wt)), len(wt),
                     heure_format(int(sum(wt) / len(wt))))
