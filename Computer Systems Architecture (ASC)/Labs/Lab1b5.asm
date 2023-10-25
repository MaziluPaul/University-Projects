;5. z=(a+b+c+1) *(a+b+c+1) /((a-b+d)*(a-b+d))

ASSUME CS:code, DS:data

data SEGMENT

a db 5
b db 2
c db 1
d db 1
rez dw ?

data ENDS

code SEGMENT

strat:
mov AX, data
mov DS, AX

mov AL, a
sub AL, b
add AL, d

; AX = (a-b+d)
mov AH, 0
mov CX, AX
mul CX
; AX = (a-b+d)*(a-b+d)
mov CX, AX
; CX = (a-b+d)*(a-b+d)

mov AL, 1
add AL, a
add AL, b
add AL, c
mov AH, 0
mov BX, AX
; BX = a+b+c+1

mul BX
; AX = (a+b+c+1) *(a+b+c+1)
cwd
idiv CX

mov rez, AX
mov AX,4C00h
int 21h

code ENDS
END strat
